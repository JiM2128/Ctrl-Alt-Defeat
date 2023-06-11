public interface InputVarifier {

    // Methods for checking if input is correct
    public boolean isName(String name);
    public boolean isPhoneNumber(String number);
    public boolean isEmail(String email);
}
