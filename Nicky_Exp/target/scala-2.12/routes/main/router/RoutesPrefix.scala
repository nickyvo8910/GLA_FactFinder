// @GENERATOR:play-routes-compiler
// @SOURCE:/media/nicky/STUDY/3_GU/4thYr/PRJ/factfinderbackend/Nicky_Exp/conf/routes
// @DATE:Wed Mar 27 15:07:38 GMT 2019


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
