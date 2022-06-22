import Foundation
import UIKit
import Nuke

public class FlashImageViewManagerImpl: NSObject {
  
  @objc static public func loadImageFromUri(_ imageView: UIImageView, uri: NSString) {
    var imageRequest = ImageRequest(url: URL(string: uri as String))
    imageRequest.priority = ImageRequest.Priority(rawValue: 1) ?? .low
    imageRequest.options = [.reloadIgnoringCachedData]
    
    var options = ImageLoadingOptions()
    options.transition = .fadeIn(duration: 0.1)
    
    loadImage(with: imageRequest, options: options, into: imageView)
  }
}
