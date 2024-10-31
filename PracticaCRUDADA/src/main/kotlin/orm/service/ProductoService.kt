package orm.service


import orm.model.RespuestaHTTP
import orm.model.Producto
import orm.model.Proveedor
import orm.repository.ProductoRepository
import java.util.Date


class ProductoService {

    fun altaProducto(
        categoriaProducto: String?,
        nombreProducto: String?,
        precioSinIva: String?,
        descripcionProducto: String?,
        idProveedor: Long?,
        nombreProveedor: String?,
        direccionProveedor: String?

        ): RespuestaHTTP<out Producto?>? {
        //Comprueba que ningun dato sea nulo
        if (categoriaProducto == null ||
            nombreProducto == null ||
            precioSinIva == null ||
            descripcionProducto == null ||
            idProveedor == null ||
            nombreProveedor == null ||
            direccionProveedor == null
        ) {
            return RespuestaHTTP(
                400,
                "Ningún dato puede ser nulo",
                null
            )

        }

        if (categoriaProducto.length <= 50) {
            if (nombreProducto.length <= 50) {
                try {
                    val precioSinIvaDouble = precioSinIva.toDouble()


                    val idProducto = categoriaProducto.take(3) + nombreProducto.take(3) + nombreProveedor.take(3)
                    val precioConIva = precioSinIvaDouble * 1.21
                    val fechaAlta = Date()
                    val newProducto = Producto(
                        idProducto, categoriaProducto, nombreProducto,
                        descripcionProducto, precioSinIvaDouble, precioConIva, fechaAlta, 0,
                        Proveedor(idProveedor, nombreProveedor, direccionProveedor)
                    )
                    val newProductoRepository = ProductoRepository()
                    return newProductoRepository.altaProducto(newProducto)
                } catch (e: Exception) {
                    return RespuestaHTTP(
                        400,
                        "Precio sin IVA tiene que ser un double",
                        null
                    )
                }


            } else {
                return RespuestaHTTP(
                    400,
                    "Nombre no valido",
                    null
                )

            }
        } else {
            return RespuestaHTTP(
                400,
                "Categoría no valida",
                null
            )
        }
    }

    fun bajaProducto(
        idProducto: String?
    ): RespuestaHTTP<out Producto?>? {
        if (idProducto == null) {
            return RespuestaHTTP(
                400,
                "El id no puede ser nulo",
                null
            )
        }

        val newProductoRepository = ProductoRepository()
        return newProductoRepository.bajaProducto(idProducto)
    }

    fun modificarNombreProducto(
        idProducto: String?,
        nombreProducto: String?
    ): RespuestaHTTP<out Producto?>? {
        if (idProducto == null) {
            return RespuestaHTTP(
                400,
                "El id no puede ser nulo",
                null
            )
        }
        if (nombreProducto == null) {
            return RespuestaHTTP(
                400,
                "El nombre no puede ser nulo",
                null
            )
        } else if (nombreProducto.length <= 50) {
            return RespuestaHTTP(
                400,
                "El nombre no puede superar los 50 caracteres",
                null
            )
        }

        val newProductoRepository = ProductoRepository()
        return newProductoRepository.modificarNombreProducto(idProducto, nombreProducto)
    }

    fun modificarStockProducto(
        idProducto: String?,
        stockProducto: String?
    ): RespuestaHTTP<out Producto?>? {
        if (idProducto == null) {
            return RespuestaHTTP(
                400,
                "El id no puede ser nulo",
                null
            )
        }
        if (stockProducto == null) {
            return RespuestaHTTP(
                400,
                "El stock no puede ser nulo",
                null
            )
        }
        try{
            val stockInt = stockProducto.toInt()
            val newProductoRepository = ProductoRepository()
            return newProductoRepository.modificarStockProducto(idProducto, stockInt)
        }catch (e: Exception){
            return RespuestaHTTP(
                400,
                "El stock tiene que ser un entero",
                null
            )
        }


    }

    fun getProducto(
        idProducto: String?
    ): RespuestaHTTP<out Producto?>? {
        if (idProducto == null) {
            return RespuestaHTTP(
                400,
                "El id no puede ser nulo",
                null
            )
        }
        val newProductoRepository = ProductoRepository()
        return newProductoRepository.getProducto(idProducto)
    }

    //No hacen nada aparte de llamar al la funcion de repository
    fun getProductosConStock(
    ): RespuestaHTTP<out List<Producto?>?>? {
        val newProductoRepository = ProductoRepository()
        return newProductoRepository.getProductosConStock()
    }

    fun getProductosSinStock(
    ): RespuestaHTTP<out List<Producto>?>? {
        val newProductoRepository = ProductoRepository()
        return newProductoRepository.getProductosSinStock()
    }


}