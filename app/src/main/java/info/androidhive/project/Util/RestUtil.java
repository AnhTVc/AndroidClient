package info.androidhive.project.Util;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class RestUtil {
    /*public static String getASCIIContentFromEntity(HttpEntity entity){
        try {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n>0) {
                byte[] b = new byte[4096];
                n =  in.read(b);
                if (n>0) out.append(new String(b, 0, n));
            }
            return out.toString();
        }catch (Exception e){
            Log.d(Default.LOGTAG, e.getMessage());
        }

        return null;
    }

    public static Elements getElements(){
        Elements elements;

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("http://192.168.30.102:3000/element");
        String text = null;
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            text = getASCIIContentFromEntity(entity);

            elements = com.Project.Util.GSONUtil.convertJSONToElements(text);
            return elements;
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        return null;
    }



    public static void main(String[] mails){
        Image image = new Image();
        image.setHeight(12);
        image.setNameImg("asdasd");
        image.setSrc("adasd");
        image.setWeight(12);

        User user = new User();
        user.setBirthday("23/02/1994");
        user.setEmail("Tran Viet Anh");
        user.setFirstName("Tran");
        user.setFullName("Tran Viet Anh");
        user.setNameUser("AnhTVc");
        user.setImage(image);

        Gson gson = new Gson();
        Type type = new TypeToken<User>(){}.getType();


        String json = gson.toJson(user, type);
        System.out.print(json);

        //From gson
        Gson gson1 = new Gson();
        User user1 = gson1.fromJson("{\n" +
                "\t\"idUser\":\"2\",\n" +
                "\t\"nameUser\":\" Anh Trần\",\n" +
                "\t\"lastName\": \"a\",\n" +
                "\t\"firstName\": \"tran\",\n" +
                "\t\"fullName\": \"Tran Viet Anh\",\n" +
                "\t\"birthday\": \"23/02/1994\",\n" +
                "\t\"address\": \"Số 1\",\n" +
                "\t\"email\": \"sadas\",\n" +
                "\t\"phoneNumber\": \"asd\",\n" +
                "\t\"image\":\n" +
                "\t\t{\n" +
                "\t\t\t\"nameImg\": \"abc\",\n" +
                "\t\t\t\"height\": \"16\",\n" +
                "\t\t\t\"weight\": \"16\",\n" +
                "\t\t\t\"src\": \"https://iso.500px.com/wp-content/uploads/2014/04/20482.jpg\"\n" +
                "\t\t}\n" +
                "}", User.class);

        //System.out.print(user1.getImage().getNameImg());

        Post post = new Post();
        post.setContentPost("Mới bắt đầu");
        post.setCountFalsePost("12");
        post.setCountTruePost("12");
        post.setCreatePost("23/02/1994");
        post.setIdPost("10021");

        ArrayList<Image> images = new ArrayList<>();
        images.add(image);
        post.setImages(images);

        Tag tag = new Tag();
        tag.setNameTag("Bách Khóa");
        tag.setIdTag("123123");

        Element element = new Element();
        element.setPost(post);
        element.setTag(tag);
        element.setUser(user);


        ArrayList<Element> elements = new ArrayList<>();
        elements.add(element);

        Elements result = new Elements();
        result.setElements(elements);

        System.out.print("Log: " + com.Project.Util.GSONUtil.convertElementToJSON(result));
        //From response

    }*/
}
