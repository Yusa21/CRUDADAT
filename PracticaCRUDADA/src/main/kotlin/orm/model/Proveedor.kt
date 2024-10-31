package orm.model

import jakarta.persistence.*

@Entity
@Table(name = "proveedores")
data class Proveedor(
    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column(unique = true, length = 50, nullable = false)
    val nombre: String ="nombre",

    @Column(nullable = false)
    val direccion: String="direccion",

    @OneToMany(mappedBy = "proveedor")
    val productos: List<Producto> = emptyList()
)