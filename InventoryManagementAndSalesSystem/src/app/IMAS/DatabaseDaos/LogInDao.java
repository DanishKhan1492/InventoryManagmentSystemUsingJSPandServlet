package app.IMAS.DatabaseDaos;

import app.IMAS.Enitities.LoginEntity;

public interface LogInDao {
    public LoginEntity getAdmin();
    public String getPassword();
    public int changePassword(String password);
}
