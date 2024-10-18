package service;

import service.custom.imp.CustomerServiceImp;
import service.custom.imp.ItemServiceImp;
import util.ServiceType;

public class ServiceFactory {

    private static ServiceFactory factory;

    private ServiceFactory(){

    }

    public static ServiceFactory getFactory() {
        return factory!=null ? factory :(
                   factory = new ServiceFactory()
                );
    }

    public <T extends SuperService>T getServiceType(ServiceType serviceType){
            switch (serviceType){
                case CUSTOMER: return (T) new CustomerServiceImp();
                case ITEM: return (T) new ItemServiceImp();
            }
        return null;
    }
}
