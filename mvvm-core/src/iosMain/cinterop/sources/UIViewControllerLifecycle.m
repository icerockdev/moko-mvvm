/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

#import "UIViewControllerLifecycle.h"
#import <objc/runtime.h>

static void *lifecycleDelegatesArrayKey;

@implementation UIViewController (Lifecycle)

+ (void)load {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        [UIViewController swizzleMethod:@selector(willMoveToParentViewController:) withMethod:@selector(lifecycle_willMoveToParentViewController:)];
        [UIViewController swizzleMethod:@selector(didMoveToParentViewController:) withMethod:@selector(lifecycle_didMoveToParentViewController:)];
    });
}

+ (void)swizzleMethod:(SEL)originalSelector withMethod:(SEL)swizzledSelector {
    Class class = [self class];

    Method originalMethod = class_getInstanceMethod(class, originalSelector);
    Method swizzledMethod = class_getInstanceMethod(class, swizzledSelector);

    BOOL didAddMethod = class_addMethod(class,
            originalSelector,
            method_getImplementation(swizzledMethod),
            method_getTypeEncoding(swizzledMethod)
    );

    if (didAddMethod) {
        class_replaceMethod(class,
                swizzledSelector,
                method_getImplementation(originalMethod),
                method_getTypeEncoding(originalMethod)
        );
    } else {
        method_exchangeImplementations(originalMethod, swizzledMethod);
    }
}

#pragma mark - delegates control

- (NSArray *)lifecycleDelegates {
    return objc_getAssociatedObject(self, &lifecycleDelegatesArrayKey);
}

- (NSMutableArray *)lifecycleDelegatesMutable {
    NSMutableArray *result = objc_getAssociatedObject(self, &lifecycleDelegatesArrayKey);
    if (result == nil) {
        result = [NSMutableArray new];
        objc_setAssociatedObject(self, &lifecycleDelegatesArrayKey, result, OBJC_ASSOCIATION_RETAIN);
    }
    return result;
}

- (NSUInteger)addLifecycleDelegate:(id <UIViewControllerLifecycleDelegate>)delegate {
    NSMutableArray *array = self.lifecycleDelegatesMutable;
    NSUInteger index = array.count;
    [array addObject:delegate];
    return index;
}

- (void)removeLifecycleDelegate:(NSUInteger)index {
    [self.lifecycleDelegatesMutable removeObjectAtIndex:index];
}

#pragma mark - Method Swizzling

- (void)lifecycle_willMoveToParentViewController:(nullable UIViewController *)parent {
    [self lifecycle_willMoveToParentViewController:parent];

    NSArray *delegates = self.lifecycleDelegates;
    if (delegates == nil) return;

    for (id <UIViewControllerLifecycleDelegate> delegate in delegates) {
        [delegate viewController:self willMoveToParentViewController:parent];
    }
}

- (void)lifecycle_didMoveToParentViewController:(nullable UIViewController *)parent {
    [self lifecycle_didMoveToParentViewController:parent];

    NSArray *delegates = self.lifecycleDelegates;
    if (delegates == nil) return;

    for (id <UIViewControllerLifecycleDelegate> delegate in delegates) {
        [delegate viewController:self didMoveToParentViewController:parent];
    }
}

@end
