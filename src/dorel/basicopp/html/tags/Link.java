package dorel.basicopp.html.tags;

import dorel.basicopp.html.helpers.HtmlElementHelper;


public class Link extends HtmlElementHelper  {
    public Link(String rel, String mimeType, String href) {
        super("link", "");
        addAtribute("rel", rel);
        addAtribute("type", mimeType);
        addAtribute("href", href);
    }
}

//    public static enum REL {
//
//        alternate,
//        archives,
//        author,
//        bookmark,
//        external,
//        first,
//        help,
//        icon,
//        last,
//        license,
//        next,
//        nofollow,
//        noreferrer,
//        pingback,
//        prefetch,
//        prev,
//        search,
//        sidebar,
//        stylesheet,
//        tag,
//        up
//    }

//    public static enum MIME_TYPE {
//
//        application,
//        audio,
//        example,
//        image,
//        message,
//        model,
//        multipart,
//        text,
//        video
//    }

