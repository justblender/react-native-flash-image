import Foundation
import UIKit
import Nuke

@objc
public class FlashImageNativeView: UIImageView {
    
  private var currentImageSource: String? = nil
  
  @objc
  public func setSource(
    _ requestUri: String,
    requestHeaders: Array<String>,
    requestPriority: Int,
    requestCachePolicy: String,
    progress: @escaping ((_ completed: Double, _ total: Double) -> Void),
    completion: @escaping ((_ width: Int32, _ height: Int32, _ error: String?) -> Void)
  ) {
    let imageRequest = FlashImageNativeView.createImageRequest(
      requestUri: requestUri,
      requestHeaders: requestHeaders,
      requestPriority: requestPriority,
      requestCachePolicy: requestCachePolicy
//      signature: nil
    )
    
    var options = ImageLoadingOptions()
    options.transition = .fadeIn(duration: 0.1)
    options.alwaysTransition = true
    
    Nuke.loadImage(with: imageRequest, options: options, into: self, progress: { _, completed, total in
      progress(Double(completed), Double(total))
    }) { result in
      switch result {
      case .success(let response):
        let size = response.image.size
        completion(Int32(size.width), Int32(size.height), nil)
        
      case .failure(let error):
        completion(Int32.zero, Int32.zero, error.localizedDescription)
      }
    }
  }
  
  private static func createImageRequest(
    requestUri: String,
    requestHeaders: Array<String>,
    requestPriority: Int,
    requestCachePolicy: String
//    signature: String?
  ) -> ImageRequest {
    var urlRequest = URLRequest(url: URL(string: requestUri)!)
    
    let requestHeaderChunks = stride(from: 0, to: requestHeaders.count, by: 2).map {
      Array(requestHeaders[$0..<min($0 + 2, requestHeaders.count)])
    }
    
    for requestHeader in requestHeaderChunks {
      if requestHeader.count == 2 {
        urlRequest.addValue(requestHeader[1], forHTTPHeaderField: requestHeader[0])
      }
    }
    
    var imageRequest = ImageRequest(urlRequest: urlRequest)
//    imageRequest.userInfo[.imageIdKey] = requestUri + (signature ?? "")
    imageRequest.options = getRequestOptions(requestCachePolicy)
    imageRequest.priority = getRequestPriority(requestPriority)
    return imageRequest
  }
  
  private static func getRequestPriority(_ priorityValue: Int) -> ImageRequest.Priority {
    return ImageRequest.Priority(rawValue: priorityValue) ?? .normal
  }
  
  private static func getRequestOptions(_ cachePolicyValue: String) -> ImageRequest.Options {
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
