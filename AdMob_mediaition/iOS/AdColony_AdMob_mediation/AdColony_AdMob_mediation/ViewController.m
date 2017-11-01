//
//  ViewController.m
//  AdColony_AdMob_mediation
//
//  Created by minji song on 10/26/17.
//  Copyright © 2017 minji song. All rights reserved.
//

@import GoogleMobileAds;

#import "ViewController.h"

@interface ViewController () <GADInterstitialDelegate,GADRewardBasedVideoAdDelegate>
@property (weak, nonatomic) IBOutlet UILabel *appIdLabel;
@property (weak, nonatomic) IBOutlet UILabel *interstitialLabel;;
@property (weak, nonatomic) IBOutlet UILabel *rewardedLabel;
@property (weak, nonatomic) IBOutlet UIButton *interstitialRequestButton;
@property (weak, nonatomic) IBOutlet UIButton *interstitialShowButton;
@property (weak, nonatomic) IBOutlet UIButton *rewardedRequestButton;
@property (weak, nonatomic) IBOutlet UIButton *rewardedShowButton;

@property(nonatomic, strong) GADInterstitial *interstitial;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    [_appIdLabel setText:kAppID];
    [_interstitialLabel setText:kInterstitialUnitID];
    [_rewardedLabel setText:kRewardedUnitID];
    
}

- (IBAction)initializeButtonClicked:(id)sender {
    
    // Initialize Google Mobile Ads SDK
    // Sample AdMob app ID: ca-app-pub-3940256099942544~1458002511
    [GADMobileAds configureWithApplicationID:kAppID];
    
    self.interstitial = [[GADInterstitial alloc]
                         initWithAdUnitID:kInterstitialUnitID];
    self.interstitial.delegate = self;
    
    [GADRewardBasedVideoAd sharedInstance].delegate = self;
    
    // Enable request buttons
    [_interstitialRequestButton setEnabled:true];
    [_rewardedRequestButton setEnabled:true];
    
}

/// Tells the delegate an ad request succeeded.
- (void)interstitialDidReceiveAd:(GADInterstitial *)ad {
    [_interstitialShowButton setEnabled:true];
}

/// Tells the delegate the interstitial had been animated off the screen.
- (void)interstitialDidDismissScreen:(GADInterstitial *)ad {
    [_interstitialShowButton setEnabled:false];
}

/// Tells the delegate an rewarded ad request succeeded.
- (void)rewardBasedVideoAdDidReceiveAd:
(nonnull GADRewardBasedVideoAd *)rewardBasedVideoAd {
    [_rewardedShowButton setEnabled:true];
}

/// Tells the delegate the rewarded had been closed.
- (void)rewardBasedVideoAdDidClose:
(nonnull GADRewardBasedVideoAd *)rewardBasedVideoAd {
    [_rewardedShowButton setEnabled:false];
}

- (IBAction)interstitialRequestButtonClicked:(id)sender {
    GADRequest *request = [GADRequest request];
    [self.interstitial loadRequest:request];
}

- (IBAction)interstitialShowButtonClicked:(id)sender {
    if (self.interstitial.isReady) {
        [self.interstitial presentFromRootViewController:self];
    }
}

- (IBAction)rewardedRequestButtonClicked:(id)sender {
    [[GADRewardBasedVideoAd sharedInstance] loadRequest:[GADRequest request]
                                           withAdUnitID:kRewardedUnitID];
}

- (IBAction)rewardedShowButtonClicked:(id)sender {
    [[GADRewardBasedVideoAd sharedInstance] presentFromRootViewController:self];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end

