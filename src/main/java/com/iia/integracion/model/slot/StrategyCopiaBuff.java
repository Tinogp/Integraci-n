package com.iia.integracion.model.slot;

import java.util.ArrayList;
import java.util.List;

import com.iia.integracion.model.mensaje.Mensaje;

public class StrategyCopiaBuff implements StrategyAcceso {

    @Override
    public Mensaje acceder(List<Mensaje> buff) {
        for (Mensaje m : buff) {
            buff.remove(m);
        }
        return null;
    }

    public List<Mensaje> copiarBuff(List<Mensaje> buff) {
        return new ArrayList<>(buff);
    }

}
