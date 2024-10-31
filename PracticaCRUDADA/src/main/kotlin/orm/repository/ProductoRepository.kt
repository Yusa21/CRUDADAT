package orm.repository

import jakarta.persistence.EntityManager
import orm.model.Producto
import orm.model.RespuestaHTTP
import orm.utils.HibernateUtils

class ProductoRepository {

    //Crea un producto completamente nuevo si el id no existe
    fun altaProducto(
        producto: Producto
    ): RespuestaHTTP<out Producto?>? {
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()
            em.transaction.begin()

            // Comprueba si el producto existe
            val existingProducto = em.find(Producto::class.java, producto.id)
            if (existingProducto != null) {
                return RespuestaHTTP(
                    409,
                    "Producto con ID ${producto.id} ya existe",
                    existingProducto
                )
            }

            //Si no existe lo añade
            em.persist(producto)
            em.transaction.commit()

            return RespuestaHTTP(
                201,
                "Producto creado exitosamente",
                producto
            )
        } catch (e: Exception) {
            em?.transaction?.rollback()
            return RespuestaHTTP(
                500,
                "Error al crear producto: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    // Borrar un producto segun su ID
    fun bajaProducto(id: String): RespuestaHTTP<Producto?>? {
        var em: EntityManager? = null
        try {
            var em = HibernateUtils.getEntityManager()
            em.transaction.begin()

            val producto = em.find(Producto::class.java, id)
                if(producto == null){
                    return RespuestaHTTP(
                        404,
                        "Producto con ID $id no encontrado",
                        null
                    )
                }
            //Si lo encuntra lo borra
            em.remove(producto)
            em.transaction.commit()

            return RespuestaHTTP(
                200,
                "Producto eliminado exitosamente",
                producto
            )
        } catch (e: Exception) {
            em?.transaction?.rollback()
            return RespuestaHTTP(
                500,
                "Error al eliminar producto: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    //Modifica el nombre de un producto
    fun modificarNombreProducto(id: String, nuevoNombre: String): RespuestaHTTP<Producto?>? {
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()
            em.transaction.begin()

            var producto = em.find(Producto::class.java, id)
            if(producto == null){
                return RespuestaHTTP(
                    404,
                    "Producto con ID $id no encontrado",
                    null
                )
            }

            producto.nombre = nuevoNombre
            em.merge(producto)
            em.transaction.commit()

            return RespuestaHTTP(
                200,
                "Nombre de producto modificado exitosamente",
                producto
            )
        } catch (e: Exception) {
            em?.transaction?.rollback()
            return RespuestaHTTP(
                500,
                "Error al modificar nombre de producto: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    //Modifica el stock de un producto
    fun modificarStockProducto(id: String, nuevoStock: Int): RespuestaHTTP<Producto?>? {
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()
            em.transaction.begin()

            var producto = em.find(Producto::class.java, id)
            if(producto == null){
                return RespuestaHTTP(
                    404,
                    "Producto con ID $id no encontrado",
                    null
                )
            }

            producto.stock = nuevoStock
            em.merge(producto)
            em.transaction.commit()

            return RespuestaHTTP(
                200,
                "Stock de producto modificado exitosamente",
                producto
            )
        } catch (e: Exception) {
            em?.transaction?.rollback()
            return RespuestaHTTP(
                500,
                "Error al modificar nombre de producto: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }



    //Devuelve el objeto dentro de RespuestaHTTP
    fun getProducto(id: String): RespuestaHTTP<Producto?>? {
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()

            val producto = em.find(Producto::class.java, id)
            if(producto == null){
                return RespuestaHTTP(
                    404,
                    "Producto con ID $id no encontrado",
                    null
                )
            }

            return RespuestaHTTP(
                200,
                "Producto encontrado",
                producto
            )
        } catch (e: Exception) {
            return RespuestaHTTP(
                500,
                "Error al obtener producto: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun getProductosConStock(): RespuestaHTTP<out List<Producto?>?>? {
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()

            val productos = em.createQuery(
                "SELECT p FROM Producto p WHERE p.stock > 0",
                Producto::class.java
            ).resultList

            return RespuestaHTTP(
                200,
                "Productos con stock encontrados",
                productos
            )
        } catch (e: Exception) {
            return RespuestaHTTP(
                500,
                "Error al obtener productos con stock: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun getProductosSinStock(): RespuestaHTTP<out List<Producto>?>? {
        var em: EntityManager? = null
        try {
            em = HibernateUtils.getEntityManager()

            val productos = em.createQuery(
                "SELECT p FROM Producto p WHERE p.stock <= 0",
                Producto::class.java
            ).resultList

            return RespuestaHTTP(
                200,
                "Productos sin stock encontrados",
                productos
            )
        } catch (e: Exception) {
            return RespuestaHTTP(
                500,
                "Error al obtener productos sin stock: ${e.message}",
                null
            )
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }
}



