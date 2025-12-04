package com.iia.integracion.model.slot;

import java.util.ArrayList;
import java.util.List;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.concurrent.BlockingQueue;

public class StrategyCopiaBuff implements StrategyAcceso {

    @Override
    public Mensaje acceder(BlockingQueue<Mensaje> buff) {
        return null;
    }

    public List<Mensaje> copiarBuff(BlockingQueue<Mensaje> buff) {
        return new ArrayList<>(buff);
    }

}
