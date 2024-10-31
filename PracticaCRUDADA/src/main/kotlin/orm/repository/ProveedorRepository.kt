package orm.repository

import jakarta.persistence.EntityManager
import orm.model.Producto
import orm.model.Proveedor
import orm.model.RespuestaHTTP
import orm.utils.HibernateUtils

class ProveedorRepository(){

    //Devuelve un proveedor del producto
    fun getProveedorProducto(idProducto: String): RespuestaHTTP<Proveedor?>? {
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()

            val proveedor = try {
                em.createQuery(
                    "SELECT proveedor FROM Producto p WHERE p.id = :idProducto",
                    Proveedor::class.java
                )
                    .setParameter("idProducto", idProducto)
                    .singleResult
            } catch (e: Exception) {
                null
            }
            if(proveedor == null){
                return RespuestaHTTP(
                    404,
                    "No se ha encontrado un proveedor para el producto, esto no deberia ser posible",
                    null
                )
            }

            return RespuestaHTTP(
                200,
                "Proveedor encontrado",
                proveedor
            )
        } catch (e: Exception) {
            return RespuestaHTTP(
                500,
                "Error al obtener proveedor: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    //Devuelve una lista con todos los proveedores
    fun getTodosProveedores(): RespuestaHTTP<out List<Proveedor?>?> {
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()

            val proveedores = em.createQuery(
                "SELECT p FROM Proveedor p ",
                Proveedor::class.java
            ).resultList

            return RespuestaHTTP(
                200,
                "Proveedores encontrados",
                proveedores
            )
        } catch (e: Exception) {
            return RespuestaHTTP(
                500,
                "Error al obtener proveedores: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    //Se asegura de que exista el nombre de un proveedor
    fun getProveedorByName(nombre: String):RespuestaHTTP<Proveedor?>?{
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()

            val proveedor = try {
                em.createQuery(
                    "SELECT p FROM Proveedor p WHERE p.nombre = :nombre",
                    Proveedor::class.java
                )
                    .setParameter("nombre", nombre)
                    .singleResult
            } catch (e: Exception) {
                null
            }
            if(proveedor == null){
                return RespuestaHTTP(
                    404,
                    "No se ha encontrado un proveedor con el nombre ${nombre}",
                    null
                )
            }

            return RespuestaHTTP(
                200,
                "Proveedor ${nombre} emcontrado",
                proveedor
            )
        } catch (e: Exception) {
            return RespuestaHTTP(
                500,
                "Error al obtener proveedor: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun proveedorExists(nombreProveedor: String, direccionProveedor: String):RespuestaHTTP<out Proveedor?>? {
        var em: EntityManager? = null
        try {
            var em = HibernateUtils.getEntityManager()
            em.transaction.begin()

            val proveedor = try {
                em.createQuery(
                    "SELECT p FROM Proveedor p WHERE p.nombre = :nombre AND p.direccion = :direccion",
                    Proveedor::class.java
                )
                    .setParameter("nombre", nombreProveedor)
                    .setParameter("direccion", direccionProveedor)
                    .singleResult
            } catch (e: Exception) {
                return RespuestaHTTP(
                    404,
                    "Proveedor con nombre $nombreProveedor no encontrado",
                    null
                )
            }

            return RespuestaHTTP(
                200,
                "Producto eliminado exitosamente",
                proveedor
            )
        } catch (e: Exception) {
            em?.transaction?.rollback()
            return RespuestaHTTP(
                500,
                "Error al encontrar el proveedor: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }
}
