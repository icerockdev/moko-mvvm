/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

#import <UIKit/UIWindow.h>

NS_ASSUME_NONNULL_BEGIN

@protocol UIViewLifecycleDelegate <NSObject>
- (void)view:(UIView *)view willMoveToWindow:(nullable UIWindow *)window;

- (void)viewDidMoveToWindow:(UIView *)view;
@end

@interface UIView (Lifecycle)

- (NSValue *)addLifecycleDelegate:(id <UIViewLifecycleDelegate>)delegate;

- (void)removeLifecycleDelegate:(NSValue *)delegate;

@end

NS_ASSUME_NONNULL_END
