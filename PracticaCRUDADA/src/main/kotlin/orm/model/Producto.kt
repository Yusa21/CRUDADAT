package orm.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "productos")
data class Producto(
    @Id
    val id: String = "",

    @Column(nullable = false, length = 10)
    val categoria: String = "categoria",

    @Column(nullable = false, length = 50)
    var nombre: String = "nombre",

    @Column
    val descripcion: String = "descripcion",

    @Column(nullable = false)
    val precioSinIva: Double = 0.0,

    @Column(nullable = false)
    val precioConIva: Double = 0.0,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val fechaAlta: Date = Date(),

    @Column(nullable = false)
    var stock: Int = 0,

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    val proveedor: Proveedor = Proveedor()
)