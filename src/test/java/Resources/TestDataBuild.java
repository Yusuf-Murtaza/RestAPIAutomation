package Resources;

import Pojo.AddBook;

public class TestDataBuild {

    public AddBook addBookPayload() {


        AddBook p = new AddBook();

        p.setName("Rest Assured Automation");
        p.setAisle("2516");
        p.setIsbn("wipr");
        p.setAuthor("Yusuf Murtaza");
        return p;
    }

        public String deleteBookPayload(String placeId) {

        return "{\r\n \"ID\" : \""+placeId+"\"\r\n}";
}

}