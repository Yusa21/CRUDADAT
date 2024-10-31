package orm.model

import jakarta.persistence.*

@Entity
@Table(name="usuarios")
data class Usuario(
    @Id
    val nombreUsuario: String="nombre",

    @Column(length = 20, nullable = false)
    val password: String ="password",
)