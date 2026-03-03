package ra.coursemanagement.business;

import ra.coursemanagement.model.Admin;

public interface IAdminService extends IBaseService <Admin,Integer>{
    Admin findByUsername(String username);
    Admin login(String username, String pass);

}
