/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

#import <UIKit/UIWindow.h>
#import <UIKit/UIViewController.h>

NS_ASSUME_NONNULL_BEGIN

@protocol UIViewControllerLifecycleDelegate <NSObject>
- (void)viewController:(UIViewController *)viewController willMoveToParentViewController:(nullable UIViewController *)parent;

- (void)viewController:(UIViewController *)viewController didMoveToParentViewController:(nullable UIViewController *)parent;
@end

@interface UIViewController (Lifecycle)

- (NSUInteger)addLifecycleDelegate:(id <UIViewControllerLifecycleDelegate>)delegate;

- (void)removeLifecycleDelegate:(NSUInteger)index;

@end

NS_ASSUME_NONNULL_END
