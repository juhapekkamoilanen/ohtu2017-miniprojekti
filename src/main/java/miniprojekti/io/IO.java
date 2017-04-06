/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojekti.io;

import miniprojekti.domain.Article;
import java.util.Collection;

/**
 *
 * @author Joonas
 */
public class IO {

    private BibFileWriter fileWriter;
    private FormattedStringBufferBuilder bufferBuilder;

    public IO (BibFileWriter bfw, FormattedStringBufferBuilder rsbb) {
        fileWriter = bfw;
        bufferBuilder = rsbb;
    }

    /**
     * Write bib file for the given references
     * @param articles Collection of references
     * @param filename A filename given by the user.
     */
    public void writeBibFile(String filename,Collection<Article> articles) {
        //! Here a new file name is set for filewriter, not a smart solution(?).
        // The filewriter has been initialized in the App class with a different file name.
        // Should be fixed. -Roosa
        fileWriter.setName(filename);
        fileWriter.writeFile(bufferBuilder.formatReferences(articles).toString());
    }
}
