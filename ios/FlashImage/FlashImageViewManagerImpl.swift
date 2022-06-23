import Foundation
import UIKit
import Nuke

public class FlashImageViewManagerImpl: NSObject {
  
  @objc static public func loadImage(
    _ imageView: UIImageView,
    sourceUri: NSString,
    priorityValue: NSNumber,
    cacheControlValue: NSString,
    progress: @escaping ((_ completed: NSNumber, _ total: NSNumber) -> Void),
    completion: @escaping ((_ width: NSNumber?, _ height: NSNumber?, _ error: NSString?) -> Void)
  ) {
    var imageRequest = ImageRequest(url: URL(string: String(sourceUri)))
    imageRequest.options = getRequestOptions(cacheControlValue)
    imageRequest.priority = getRequestPriority(priorityValue)
    
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
  
  static private func getRequestPriority(_ priorityValue: NSNumber) -> ImageRequest.Priority {
    return ImageRequest.Priority(rawValue: Int(truncating: priorityValue)) ?? .normal
  }
  
  static private func getRequestOptions(_ cacheControlValue: NSString) -> ImageRequest.Options {
    switch cacheControlValue {
    case "ignore-cache":
      return [.reloadIgnoringCachedData]
    case "only-if-cached":
      return [.returnCacheDataDontLoad]
    default:
      return []
    }
  }
}
