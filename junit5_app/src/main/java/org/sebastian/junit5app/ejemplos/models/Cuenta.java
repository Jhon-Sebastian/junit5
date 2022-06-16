package org.sebastian.junit5app.ejemplos.models;

import org.sebastian.junit5app.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

/**
 * @author jhonc
 * @version 1.0
 * @since 1/12/2021
 */
public class Cuenta {

    private String persona;

    //TODO: Es un decimal de alta presicion, diseñado para trabajar con muchos decimales
    private BigDecimal saldo;

    private Banco banco;

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }


    // Metodo debido = debe
    public void debito(BigDecimal monto) {
        //El valor no cambia hasta ser asignado, aqui solo se hace una operación
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new DineroInsuficienteException("Dinero Insuficiente");
        }
       //Asignando valor de BigDecimal al atributo
        this.saldo = nuevoSaldo;
    }


    // Metodo credito = tiene
    public void credito(BigDecimal monto) {
        //Asignando el valor al atributo
        this.saldo = this.saldo.add(monto);
    }


    @Override
    public boolean equals(Object obj) {
        //Este if tambien valido que sea !== null
        if (!(obj instanceof Cuenta)) {
            return false;
        }
        Cuenta c = (Cuenta) obj;
        if (this.persona == null || this.saldo == null) {
            return false;
        }
        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}
