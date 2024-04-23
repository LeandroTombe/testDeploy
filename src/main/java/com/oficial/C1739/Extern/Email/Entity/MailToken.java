package com.oficial.C1739.Extern.Email.Entity;


import com.oficial.C1739.Entity.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

//con esta clase vamos a tener el token para validar el correo
//este token lo tenemos que persistir para poder comprobar si el usuario esta habilitado o no

@Getter
@Setter
@Entity
@NoArgsConstructor
public class MailToken {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMailToken;

    private String token;

    private Date expiracionToken;

    private static final int tiempo_expiracion = 15;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;




    public MailToken(Usuario usuario, String token) {
        super();
        this.token = token;
        this.usuario=usuario;
        this.expiracionToken=this.getTiempoExpiracionToken();
    }

    public MailToken(String token) {
        super();
        this.token = token;
        this.expiracionToken=this.getTiempoExpiracionToken();
    }

    private Date getTiempoExpiracionToken() {
        //El método getInstance() devuelve un objeto Calendar inicializado con la fecha y hora actual del sistema.
        Calendar calendar = Calendar.getInstance();

        //Se obtiene la fecha y hora actual del sistema utilizando new Date().
        //Luego, se llama al método getTime() del objeto Date para obtener el número de milisegundos
        calendar.setTimeInMillis(new Date().getTime());

        //Se agrega un cierto número de minutos a la fecha y hora almacenada en el objeto Calendar, en este caso el usuairo no debe tardar mas de 15min en validar su cuenta

        calendar.add(Calendar.MINUTE, tiempo_expiracion);
        //Se obtiene la fecha y hora actual del objeto Calendar utilizando calendar.getTime().
        //Nuevamente, se llama a getTime() para obtener la cantidad de milisegundos
        return new Date(calendar.getTime().getTime());
    }



    public Long getIdMailToken() {
        return idMailToken;
    }

    public void setIdMailToken(Long idMailToken) {
        this.idMailToken = idMailToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiracionToken() {
        return expiracionToken;
    }

    public void setExpiracionToken(Date expiracionToken) {
        this.expiracionToken = expiracionToken;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
