/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

#import "UIViewLifecycle.h"
#import <objc/runtime.h>

static void *lifecycleDelegatesArrayKey;

@implementation UIView (Lifecycle)

+ (void)load {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        [UIView swizzleMethod:@selector(willMoveToWindow:) withMethod:@selector(lifecycle_willMoveToWindow:)];
        [UIView swizzleMethod:@selector(didMoveToWindow) withMethod:@selector(lifecycle_didMoveToWindow)];
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

- (NSMutableArray *)lifecycleDelegates {
    NSMutableArray *result = objc_getAssociatedObject(self, &lifecycleDelegatesArrayKey);
    if (result == nil) {
        result = [NSMutableArray new];
        objc_setAssociatedObject(self, &lifecycleDelegatesArrayKey, result, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    }
    return result;
}

- (NSValue *)addLifecycleDelegate:(id)delegate {
    NSValue *item = [NSValue valueWithNonretainedObject:delegate];
    [self.lifecycleDelegates addObject:item];
    return item;
}

- (void)removeLifecycleDelegate:(NSValue *)item {
    [self.lifecycleDelegates removeObject:item];
}

#pragma mark - Method Swizzling

- (void)lifecycle_willMoveToWindow:(UIWindow *)window {
    [self lifecycle_willMoveToWindow:window];

    for (NSValue *value in self.lifecycleDelegates) {
        id <UIViewLifecycleDelegate> delegate = value.nonretainedObjectValue;
        [delegate view:self willMoveToWindow:window];
    }
}

- (void)lifecycle_didMoveToWindow {
    [self lifecycle_didMoveToWindow];

    for (NSValue *value in self.lifecycleDelegates) {
        id <UIViewLifecycleDelegate> delegate = value.nonretainedObjectValue;
        [delegate viewDidMoveToWindow:self];
    }
}

@end
