import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Admin on 07/06/2016.
 */
public class ParseCode {
    public  String[] wifiCommands;
    public  String[] LCDCommands;
    public  String[] cellularCommands;
    public  String[] audioCommands;
    public  String[] GPSCommands;
    public  ArrayList<File> javaFiles;


    public ParseCode(String sourcePath) {
        javaFiles=new ArrayList<>();
        listf(sourcePath,javaFiles);
        GetAllCommandFromSourceCode();
    }


    public void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);
        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if ((file.isFile())&&(isJavaFile(file.getName()))) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }
    public boolean isJavaFile(String fileName)
    {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            if(fileName.substring(i+1).toLowerCase().equals("java")) return  true;
        }
        return  false;
    }

    public  void GetAllCommandFromSourceCode() {
        for (File file : javaFiles) {
            GetWifiCommand(file.getPath());
            GetLCDCommand(file.getPath());
            GetCellularCommand(file.getPath());
            GetAudioCommand(file.getPath());
            GetGPSCommand(file.getPath());
        }
    }

    public void GetWifiCommand(String filePath) {
        String src = null;
        try {
            src = new String( Files.readAllBytes( Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CompilationUnit cu = null;
        try {
            cu = SourceToCompilationUnit(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(final MethodCallExpr n, final Void arg) {

                if (n.getName().equals("setWifiEnabled")) {
                    if(n.getArgs().toString().equals("[false]")) wifiCommands=AddStringToArray( wifiCommands,"wifi.setWifiEnabled(false)");
                    if(n.getArgs().toString().equals("[true]")) wifiCommands=AddStringToArray( wifiCommands,"wifi.setWifiEnabled(true)");
                }
                if(n.getName().equals("execute")) {
                    if(n.getArgs().toString().equals("[httpget, responseHandler]")) wifiCommands=AddStringToArray( wifiCommands,"Client.execute(httpget, responseHandler)");
                }

                super.visit(n, arg);
            }
        }, null);
    }
    public void GetLCDCommand(String filePath) {

        String src = null;
        try {
            src = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CompilationUnit cu = null;
        try {
            cu = SourceToCompilationUnit(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(final MethodCallExpr n, final Void arg) {
                if(n.getName().equals("lockNow")) {
                    LCDCommands=AddStringToArray( LCDCommands,"devicePolicyManager.lockNow()");
                }
                super.visit(n, arg);
            }
        }, null);

        //

        try {
            src = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            cu = SourceToCompilationUnit(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(final MethodCallExpr n, final Void arg) {
                if(n.getName().equals("acquire")) {
                    LCDCommands=AddStringToArray( LCDCommands,"wakeLock.acquire()");
                }
                super.visit(n, arg);
            }
        }, null);
    }
    public void GetCellularCommand(String filePath) {
        String src = null;
        try {
            src = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CompilationUnit cu = null;
        try {
            cu = SourceToCompilationUnit(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(final MethodCallExpr n, final Void arg) {

                if (n.getName().equals("setMobileDataEnabled")) {
                    if(n.getArgs().toString().equals("[true]"))cellularCommands=AddStringToArray( cellularCommands,"setMobileDataEnabled(getApplicationContext(),true)");
                    if(n.getArgs().toString().equals("[false]"))cellularCommands=AddStringToArray( cellularCommands,"setMobileDataEnabled(getApplicationContext(),false)");
                }
                if(n.getName().equals("execute")) {
                    if(n.getArgs().toString().equals("[httpget, responseHandler]"))cellularCommands=AddStringToArray( cellularCommands,"Client.Execute(httpget, responseHandler)");
                }
                super.visit(n, arg);
            }
        }, null);
    }
    public void GetAudioCommand(String filePath) {
        String src = null;
        try {
            src = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CompilationUnit cu = null;
        try {
            cu = SourceToCompilationUnit(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(final MethodCallExpr n, final Void arg) {
                if(n.getName().equals("start"))audioCommands=AddStringToArray( audioCommands,"mediaPlayer.start()");
                if(n.getName().equals("stop"))audioCommands=AddStringToArray( audioCommands,"mediaPlayer.stop()");
                super.visit(n, arg);
            }
        }, null);
    }
    public void GetGPSCommand(String filePath) {
        String src = null;
        try {
            src = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CompilationUnit cu = null;
        try {
            cu = SourceToCompilationUnit(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(final MethodCallExpr n, final Void arg) {

                if (n.getName().equals("putExtra")) {
                    if(n.getArgs().toString().equals("[false]")) GPSCommands=AddStringToArray( GPSCommands,"intent.putExtra(String, false)");
                    if(n.getArgs().toString().equals("[true]")) GPSCommands=AddStringToArray( GPSCommands,"intent.putExtra(String, true)");
                }
                if(n.getName().equals("requestLocationUpdates"))  GPSCommands=AddStringToArray( GPSCommands,"locationManager.requestLocationUpdates()");
                super.visit(n, arg);
            }
        }, null);
    }


    private String[] AddStringToArray(String[] arr,String input)
    {
        if(arr!=null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int i = 0; i < arr.length; i++) arrayList.add( arr[i] );
            arrayList.add( input );
            String[] newString = new String[arr.length + 1];
            arrayList.toArray( newString );
            return newString;
        }
        String[] newString={input};
        return newString;
    }
    public CompilationUnit SourceToCompilationUnit(String src) throws ParseException {
        ByteArrayInputStream in = null;
        try {
            in = new ByteArrayInputStream(src.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return JavaParser.parse(in, "UTF-8");
    }

}

