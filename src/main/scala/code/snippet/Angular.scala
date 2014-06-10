package code.snippet

import net.liftweb._
import util._
import net.liftweb.http._

import Helpers._
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.js.JsCmds.Run
import scala.xml.NodeSeq
import net.liftweb.common.{Logger, Full, Box}
import net.liftweb.json.JsonAST.{JString, JField, JObject, JArray}

/**
 * Created by andreas on 5/27/14.
 */

trait AngularApp {
  def ngApp: String
  def controller(ctrlname: String): AngularCtrl
  def toJs: String
}


object AngularApp extends RestHelper {

  serve { case "ng" :: ngApp :: Nil Get _ => respond(ngApp) }

  private var apps = Map[String, AngularApp]()

  def apply(appname: String = "myApp") =
    apps.get(appname) getOrElse {
      val a = new AngularApp {
        private var controllers = Map[String, AngularCtrl]()

        def ngApp = appname

        def controller(ctrlname: String): AngularCtrl =
          controllers.get(ctrlname) getOrElse {
            val c = AngularCtrl(this, ctrlname); controllers = controllers + (ctrlname -> c); c
          }

        def toJs = {
          def ctrls_js = (controllers.values :\ ""){_.toJs + _}
          def app_js = s"""
            |angular.module('${this.ngApp}', [])
            | $ctrls_js;
          """.stripMargin
          app_js
        }
      }
      apps = apps + (appname -> a)
      a
    }

    def respond(ngapp: String) = apps.get(ngapp) match {
      case Some(app) => JavaScriptResponse(Run(app.toJs))
      case None => NotFoundResponse("No such application")
    }

}

class
AngularSnippet {
  val myApp = AngularApp("myApp")
  def app = "^ [ng-app]" #> myApp.ngApp
  val myCtrl = myApp.controller("myCtrl")
  def controller ="^ [ng-controller]" #> myCtrl.ngController &
  "^ *" #> {
    "data-bind=username" #> ngHtmlById
  }

  def ngHtmlById = (ns: NodeSeq) => {
    val namespace = "default"
    val id = ns(0).attribute("data-bind").getOrElse("NoId")
    def xform = ns(0).label match {
      case "input" => "^ [ng-model]" #> s"$namespace.$id"
      case "select" => "^ [ng-model]" #> s"$namespace.$id"
      case "textarea" => "^ [ng-model]" #> s"$namespace.$id"
      // TODO how to implement custom form control
      case "span" => "^ [ng-bind]" #> s"$namespace.$id"
    }
    xform(ns)
  }

}




trait AngularCtrl {
  def app: AngularApp
  def ngController: String
  def toJs: String
}

object AngularCtrl {
  def apply(appl: AngularApp, ctrlname: String = "myCtrl") = new AngularCtrl {
    private var forms = Map[String,AngularForm]()

    def app = appl
    def ngController = ctrlname //app.ngApp+"."+ctrlname
    def toJs =
      s"""
        |.controller('${this.ngController}', [ '$$scope', function ($$scope) {
        | $$scope.default = new Object();
        | $$scope.default.username="Guest";
        |
        | var Api = $$resource("/ng/default.json")
        | var data = $$scope.default
        |}])
      """.stripMargin
  }
}


trait AngularForm{
}

object AngularForm {
  def apply(formname: String = "myForm") = new AngularForm {
  }
}
