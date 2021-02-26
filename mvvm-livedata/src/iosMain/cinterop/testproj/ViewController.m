/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

#import "ViewController.h"
#import "UIViewLifecycle.h"

@interface LifecycleDelegate : NSObject<UIViewLifecycleDelegate>

@end

@implementation LifecycleDelegate

- (void)view:(UIView *)view willMoveToWindow:(UIWindow *)window {
//    NSLog(@"view %@ will move to window %@", view, window);
}

- (void)viewDidMoveToWindow:(UIView *)view {
//    NSLog(@"view %@ did move to window", view);
}

@end

@interface ViewController ()

@property(nonatomic, weak) IBOutlet UILabel *text1;
@property(nonatomic, weak) IBOutlet UILabel *text2;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    if(_text1.window != nil) {
        NSLog(@"view %@ already with window %@", _text1, _text1.window);
    }
    
    LifecycleDelegate *lifecycleDelegate = [[LifecycleDelegate alloc] init];

    [_text1 addLifecycleDelegate:lifecycleDelegate];
    [_text2 addLifecycleDelegate:lifecycleDelegate];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];

    [self.navigationController popViewControllerAnimated:true];
}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
}

@end

@interface StartViewController: UIViewController

@property(nonatomic, weak) IBOutlet UIButton *button;

@end

@implementation StartViewController

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];

    [_button sendActionsForControlEvents:UIControlEventTouchUpInside];
}

@end
