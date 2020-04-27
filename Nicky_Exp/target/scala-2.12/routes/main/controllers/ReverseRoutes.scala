// @GENERATOR:play-routes-compiler
// @SOURCE:/media/nicky/STUDY/3_GU/4thYr/PRJ/factfinderbackend/Nicky_Exp/conf/routes
// @DATE:Wed Mar 27 15:07:38 GMT 2019

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:7
package controllers {

  // @LINE:7
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def socket(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "wsocket")
    }
  
    // @LINE:7
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:9
    def nlpinit(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "nlpinit")
    }
  
    // @LINE:8
    def eventDetails(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "eventDetails")
    }
  
  }

  // @LINE:13
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:13
    def versioned(file:Asset): Call = {
    
      (file: @unchecked) match {
      
        // @LINE:13
        case (file)  =>
          implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
          Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
      
      }
    
    }
  
  }


}
