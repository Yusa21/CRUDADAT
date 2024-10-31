package orm.service

import orm.model.Usuario
import orm.model.RespuestaHTTP
import orm.repository.UsuarioRepository



class UserService {
    fun login(userInput: String?, passInput: String?): RespuestaHTTP<Usuario?>? {
        if(userInput == null){
            return RespuestaHTTP(400,"Usuario no puede ser nulo",null)
        }else if(passInput == null){
            return RespuestaHTTP(400,"La contraseña no puede ser nula",null)
        }else{
            val newUsuarioRepository = UsuarioRepository()
            return newUsuarioRepository.login(userInput,passInput)
        }
    }
}