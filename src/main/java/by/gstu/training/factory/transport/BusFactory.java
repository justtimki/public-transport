package by.gstu.training.factory.transport;

import by.gstu.training.model.transport.Bus;
import by.gstu.training.model.transport.Transport;

public class BusFactory extends TransportFactory {
    @Override
    public Transport createTransport() {
        return new Bus();
    }
}
