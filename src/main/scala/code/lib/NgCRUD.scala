package code.lib

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{NoContentResponse, OkResponse, JsonResponse}
import net.liftweb.json.Extraction._


/**
 * Created by andreas on 6/8/14.
 */
object NgCRUD extends RestHelper {

  serve ( List("entities") prefix {
    case Nil JsonGet _ =>
      JsonResponse(decompose(entities.values))

    case entityId :: Nil JsonGet _ =>
      JsonResponse(decompose(entities.get(entityId)))

    case entityId :: Nil JsonPost json ->_ =>
      val newItem = json.extract[Entity]
      if (entities.get(entityId).isEmpty) entities += (newItem.id -> newItem)   //new item
      else entities += (newItem.id -> newItem)                                  //update
      JsonResponse(decompose(entities.values))

    case entityID :: Nil JsonDelete _ =>
      if (entities.get(entityID ).isDefined) {
        entities.remove(entityID)
        JsonResponse(decompose(entities.values))
      } else {
        NoContentResponse()
      }

  })

  case class Entity(id: String, field: String)

  private val entities = scala.collection.mutable.Map[String,Entity](
    "1" -> Entity("1","First")
  )

}
