#import "HRBridgeModule.h"

#import "KuiklyRenderViewController.h"
#import <OpenKuiklyIOSRender/NSObject+KR.h>
#import <OpenKuiklyIOSRender/KRBaseModule.h>
#import <OpenKuiklyIOSRender/KuiklyContextParam.h>
#import <OpenKuiklyIOSRender/KuiklyRenderView.h>
#import <OpenKuiklyIOSRender/KuiklyRenderModuleExportProtocol.h>

#define REQ_PARAM_KEY @"reqParam"
#define CMD_KEY @"cmd"
#define FROM_HIPPY_RENDER @"from_hippy_render"
// 扩展桥接接口
/*
 * @brief Native暴露接口到kotlin侧，提供kotlin侧调用native能力
 */

@implementation HRBridgeModule

@synthesize hr_rootView;

- (void)copyToPasteboard:(NSDictionary *)args {
    NSDictionary *params = [args[KR_PARAM_KEY] hr_stringToDictionary];
    NSString *content = params[@"content"];
    UIPasteboard *pasteboard = [UIPasteboard generalPasteboard];
    pasteboard.string = content;
}

- (void)log:(NSDictionary *)args {
    NSDictionary *params = [args[KR_PARAM_KEY] hr_stringToDictionary];
    NSString *content = params[@"content"];
    NSLog(@"KuiklyRender:%@", content);
}

- (void)readAssetFile:(NSDictionary *)args {
    NSDictionary *params = [args[KR_PARAM_KEY] hr_stringToDictionary];
    KuiklyRenderCallback callback = args[KR_CALLBACK_KEY];
    if (!callback) {
        return;
    }
    NSString *path = params[@"assetPath"];
    if (!path) {
        callback(@{@"result": @"", @"error": @"assetPath is nil"});
        return;
    }
    KuiklyRenderView *renderView = (KuiklyRenderView *)self.hr_rootView;
    if (!renderView) {
        callback(@{@"result": @"", @"error": @"hr_rootView is nil"});
        return;
    }
    KuiklyContextParam *contextParam = renderView.contextParam;
    if (!contextParam) {
        callback(@{@"result": @"", @"error": @"contextParam is nil"});
        return;
    }
    NSURL *pathUrl = [contextParam urlForFileName:[path stringByDeletingPathExtension] extension:[path pathExtension]];
    dispatch_async(dispatch_get_global_queue(0, 0), ^{
        NSError *error;
        NSString *jsonStr = @"";
        if (pathUrl) {
            jsonStr = [NSString stringWithContentsOfURL:pathUrl encoding:NSUTF8StringEncoding error:&error];
        } else {
            error = [NSError errorWithDomain:@"HRBridgeModule" code:404 userInfo:@{NSLocalizedDescriptionKey: @"File not found"}];
        }
        NSDictionary *result = @{
            @"result": jsonStr ?: @"",
            @"error": error ? error.localizedDescription : @""
        };
        callback(result);
    });
}

@end