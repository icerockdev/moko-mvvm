/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

#import "ViewController.h"
#import "UIViewLifecycle.h"

@interface ViewController () <UIViewLifecycleDelegate>

@property(nonatomic, weak) IBOutlet UILabel *text1;
@property(nonatomic, weak) IBOutlet UILabel *text2;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    if(_text1.window != nil) {
        NSLog(@"view %@ already with window %@", _text1, _text1.window);
    }

    [_text1 addLifecycleDelegate:self];
    [_text2 addLifecycleDelegate:self];
}

- (void)view:(UIView *)view willMoveToWindow:(UIWindow *)window {
    NSLog(@"view %@ will move to window %@", view, window);
}

- (void)viewDidMoveToWindow:(UIView *)view {
    NSLog(@"view %@ did move to window", view);
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];

    [self.navigationController popViewControllerAnimated:true];
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
