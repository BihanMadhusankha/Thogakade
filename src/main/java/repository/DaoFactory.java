package repository;

import repository.custom.imp.CustomerDaoImp;
import repository.custom.imp.ItemDaoImp;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return daoFactory!= null?daoFactory:(
                daoFactory = new DaoFactory()
                );
    }

    public <T extends SuperDao>T getDao(DaoType daoType){
        switch (daoType){
            case CUSTOMER:return (T) new CustomerDaoImp();
            case ITEM:return (T) new ItemDaoImp();
        }
        return null;
    }
}
