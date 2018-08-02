package id.co.pln.simoka.classutama;

/**
 * Created by 4741G on 08/12/2017.
 */

public class user {
    private String _username;
    private String _password;
    private int _status;
    private String _role;

    public String get_role() {
        return _role;
    }

    public void set_role(String _role) {
        this._role = _role;
    }

    public void set_username(String ival){
        _username = ival;
    }

    public String get_username(){
        return _username;
    }

    public void set_password(String ival){
        _password = ival;
    }

    public String get_password(){
        return _password;
    }

    public void set_status(int ival){
        _status = ival;
    }

    public int get_status(){
        return _status;
    }
}
