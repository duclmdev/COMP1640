//package edu.fpt.comp1640.service;
//
//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.io.*;
//
//@Path("/file")
//public class UploadFileService {
//
//    private static final String STORAGE_FOLDER = "d://uploaded/";
//
//    @POST
//    @Path("/upload")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response uploadFile(
//        @FormDataParam("file") InputStream uploadedInputStream,
//        @FormDataParam("file") FormDataContentDisposition fileDetail
//    ) {
//        String uploadedFileLocation = String.format("%s%s", STORAGE_FOLDER, fileDetail.getFileName());
//
//        // save it
//        writeToFile(uploadedInputStream, uploadedFileLocation);
//        String output = "File uploaded to : " + uploadedFileLocation;
//
//        return Response.status(200).entity(output).build();
//    }
//
//    // save uploaded file to new location
//    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
//        try (OutputStream out = new FileOutputStream(new File(uploadedFileLocation))) {
//            int read = 0;
//            byte[] bytes = new byte[1024];
//            while ((read = uploadedInputStream.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
