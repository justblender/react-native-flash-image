import Foundation
import UIKit
import Nuke

public class FlashImageViewManagerImpl: NSObject {
  
  @objc static public func loadImage(
    _ imageView: UIImageView,
    requestUri: NSString,
    requestHeaders: NSArray,
    requestPriority: NSNumber,
    requestCachePolicy: NSString,
    progress: @escaping ((_ completed: NSNumber, _ total: NSNumber) -> Void),
    completion: @escaping ((_ width: NSNumber?, _ height: NSNumber?, _ error: NSString?) -> Void)
  ) {
    let imageRequest = createImageRequest(
      requestUri: requestUri,
      requestHeaders: requestHeaders,
      requestPriority: requestPriority,
      requestCachePolicy: requestCachePolicy
    )
    
    var options = ImageLoadingOptions()
    options.transition = .fadeIn(duration: 0.1)
    options.alwaysTransition = true
    
    Nuke.loadImage(with: imageRequest, options: options, into: imageView, progress: { _, completed, total in
      progress(NSNumber(value: completed), NSNumber(value: total))
    }) { result in
      do {
        let imageResponse = try result.get()
        let size = imageResponse.image.size
        
        completion(
          NSNumber(value: Float(size.width)),
          NSNumber(value: Float(size.height)),
          nil
        )
      } catch let error {
        completion(
          nil,
          nil,
          NSString(string: error.localizedDescription)
        )
      }
    }
  }
  
  static private func createImageRequest(
    requestUri: NSString,
    requestHeaders: NSArray,
    requestPriority: NSNumber,
    requestCachePolicy: NSString
  ) -> ImageRequest {
    var urlRequest = URLRequest(url: URL(string: String(requestUri))!)
    for requestHeader in (requestHeaders as NSArray as! [NSString]) {
      let separated = requestHeader.components(separatedBy: "=")
      if separated.count == 2 {
        urlRequest.setValue(separated[1], forHTTPHeaderField: separated[0])
      }
    }
    
    var imageRequest = ImageRequest(urlRequest: urlRequest)
    imageRequest.options = getRequestOptions(requestCachePolicy)
    imageRequest.priority = getRequestPriority(requestPriority)
    return imageRequest
  }
  
  static private func getRequestPriority(_ priorityValue: NSNumber) -> ImageRequest.Priority {
    return ImageRequest.Priority(rawValue: Int(truncating: priorityValue)) ?? .normal
  }
  
  static private func getRequestOptions(_ cachePolicyValue: NSString) -> ImageRequest.Options {
    switch cachePolicyValue {
    case "ignore-cache":
      return [.reloadIgnoringCachedData]
    case "only-if-cached":
      return [.returnCacheDataDontLoad]
    default:
      return []
    }
  }
}
