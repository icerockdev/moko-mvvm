/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

#import "ViewController.h"
#import "UIViewControllerLifecycle.h"

@interface LifecycleDelegate : NSObject <UIViewControllerLifecycleDelegate>

@end

@implementation LifecycleDelegate

- (void)viewController:(nonnull UIViewController *)viewController willMoveToParentViewController:(nullable UIViewController *)parent {
    NSLog(@"viewController %@ will move to %@", viewController, parent);
}

- (void)viewController:(nonnull UIViewController *)viewController didMoveToParentViewController:(nullable UIViewController *)parent {
    NSLog(@"viewController %@ did move to %@", viewController, parent);
}

@end

@interface ViewController ()

@property(nonatomic, weak) IBOutlet UILabel *text1;
@property(nonatomic, weak) IBOutlet UILabel *text2;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    LifecycleDelegate *lifecycleDelegate = [[LifecycleDelegate alloc] init];

    [self addLifecycleDelegate:lifecycleDelegate];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];

    [self.navigationController popViewControllerAnimated:true];
}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
}

@end

@interface StartViewController : UIViewController

@property(nonatomic, weak) IBOutlet UIButton *button;

@end

@implementation StartViewController

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];

    [_button sendActionsForControlEvents:UIControlEventTouchUpInside];
}

@end
