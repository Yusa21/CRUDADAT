package orm.repository

import jakarta.persistence.EntityManager
import orm.model.Usuario
import orm.model.RespuestaHTTP
import orm.utils.HibernateUtils

class UsuarioRepository {

    //Devuelve un proveedor del producto
    fun login(userInput: String, passInput: String): RespuestaHTTP<Usuario?>? {
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()

            val usuario = try {
                em.createQuery(
                    "SELECT p FROM Usuario p WHERE p.nombreUsuario = :userInput AND p.password = :passInput",
                    Usuario::class.java
                )
                    .setParameter("userInput", userInput).setParameter("passInput", passInput)
                    .singleResult
            } catch (e: Exception) {
                null
            }
            if(usuario == null){
                return RespuestaHTTP(
                    404,
                    "El usuario o la contraseña son incorrectos",
                    null
                )
            }

            return RespuestaHTTP(
                200,
                "Sesión iniciada",
                usuario
            )
        } catch (e: Exception) {
            return RespuestaHTTP(
                500,
                "Error al iniciar sesion: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }
}