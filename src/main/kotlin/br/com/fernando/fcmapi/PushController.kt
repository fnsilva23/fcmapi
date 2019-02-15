package br.com.fernando.fcmapi

import org.json.JSONObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


@RestController
@RequestMapping()
class NoteController {

    //TOKEN do usuário
    val userDeviceID = "eFRrDJY2gPU:APA91bE4I7BLTfe0kCyVviFzTlZAPiIprztsMSFE7uI4-3PbftqO3iZVuy7QJCA_q6Cosb9N1nDrwJLCkmsQ-S-tAa8Wur_pL0s7Pwzu0fVnRhfZCge2U00VvtCu8HypzZJNyhwfIaWB"
    @PostMapping("/push")
    fun list(): String {
        pushFCMNotification(userDeviceID)
        return "OK"
    }

    // Method to send Notifications from server to client end.

    val AUTH_KEY_FCM = "AIzaSyCAO0wHBDx8GmUtTNXY06-IZ8vtssc7NGs" //Chave herdada do servidor
    val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"

// userDeviceIdKey is the device id you will query from your database

    @Throws(Exception::class)
    fun pushFCMNotification(userDeviceIdKey: String) {

        val authKey = AUTH_KEY_FCM // You FCM AUTH key
        val FMCurl = API_URL_FCM

        val url = URL(FMCurl)
        val conn = url.openConnection() as HttpURLConnection

        conn.useCaches = false
        conn.doInput = true
        conn.doOutput = true

        conn.requestMethod = "POST"
        conn.setRequestProperty("Authorization", "key=$authKey")
        conn.setRequestProperty("Content-Type", "application/json")

        val data = JSONObject()
        data.put("id", "123") //Qualquer informação
        data.put("outroparam", "teste")


        val json = JSONObject()
        json.put("to", userDeviceIdKey.trim { it <= ' ' })
        val info = JSONObject()
        info.put("title", "Notificatoin Title") // Título da notificação
        info.put("body", "Hello Test notification") // Corpo da notificação
        info.put("click_action", "br.com.fernando.detalhe") // Interceptar o click do usuário e direcionar a ação do clique

        json.put("notification", info)
        json.put("data", data) // Enviar qualquer informação

        val wr = OutputStreamWriter(conn.outputStream)
        wr.write(json.toString())
        wr.flush()
        conn.inputStream
    }
}
