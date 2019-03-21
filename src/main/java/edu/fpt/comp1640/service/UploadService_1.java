//package edu.fpt.comp1640.service;
//
//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.io.*;
//
//@Path("/upload")
//public class UploadService_1 {
//    private static final String UPLOAD_PATH = "c:/temp/";
//
//    @POST
//    @Path("/pdf")
//    @Consumes({MediaType.MULTIPART_FORM_DATA})
//    public Response uploadPdfFile(
//        @FormDataParam("file") InputStream fileInputStream,
//        @FormDataParam("file") FormDataContentDisposition fileMetaData
//    ) throws Exception {
//        try (OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + fileMetaData.getFileName()))) {
//            byte[] bytes = new byte[1024];
//
//            int read = 0;
//            while ((read = fileInputStream.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            out.flush();
//        } catch (IOException e) {
//            System.out.println("------------->" + e.getMessage());
//            throw new WebApplicationException("Error while uploading file. Please try again !!");
//        }
//        return Response.status(200).entity("Data uploaded successfully !!").build();
//    }
//}
