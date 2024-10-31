package orm.service

import orm.model.Proveedor
import orm.model.RespuestaHTTP
import orm.repository.ProveedorRepository


class ProveedorService {

    fun getProveedoresProducto(idProducto: String?): RespuestaHTTP<out Proveedor?>? {
        if (idProducto == null) {
            return RespuestaHTTP(
                400,
                "El id no puede ser nulo",
                null
            )
        }
        val newProveedorRepository = ProveedorRepository()
        return newProveedorRepository.getProveedorProducto(idProducto)
    }

    fun getTodosProveedores(): RespuestaHTTP<out List<Proveedor?>?> {
        val newProveedorRepository = ProveedorRepository()
        return newProveedorRepository.getTodosProveedores()
    }

    fun proveedorExists(nombreProveedor: String,
                        direccionProveedor: String
    ):RespuestaHTTP<out Proveedor?>? {
        val newProveedorRepository = ProveedorRepository()
        return newProveedorRepository.proveedorExists(nombreProveedor,direccionProveedor)
    }
}