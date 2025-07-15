public class PrincipalModel {
    private int principalId;
    private String principalPassword;

    public PrincipalModel(int principalId, String principalPassword){
        this.principalId = principalId;
        this.principalPassword = principalPassword;
    }

    public int getPrincipalId(){
        return principalId;
    }

    public String getPrincipalPassword(){
        return principalPassword;
    }
}
