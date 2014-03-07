import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by julia
 */

public class DynamicClassOverloader extends ClassLoader {

    private java.util.Map classesHash= new java.util.HashMap();

    public final String[] classPath;

    public DynamicClassOverloader(String[] classPath) {

        this.classPath= classPath;

    }

    protected synchronized Class loadClass(String name,boolean resolve)

            throws ClassNotFoundException

    {

        Class result= findClass(name);

        if (resolve) resolveClass(result);

        return result;

    }

    protected Class findClass(String name)

            throws ClassNotFoundException

    {

        Class result= (Class)classesHash.get(name);

        if (result!=null) {

        return result;

    }

    File f= findFile(name.replace('.','/'),".class");

    if (f==null) {

        return findSystemClass(name);

    }

    try {

          byte[] classBytes= loadFileAsBytes(f);

          result= defineClass(name,classBytes,0,classBytes.length);

    } catch (IOException e) {

        throw new ClassNotFoundException("Cannot load class "+name+": "+e);

    } catch (ClassFormatError e) {

        throw new ClassNotFoundException("Format of class file incorrect for class "+name+": "+e);

    }

    classesHash.put(name,result);

    return result;

  }

  protected java.net.URL findResource(String name) {

    File f= findFile(name,"");

    if (f==null) return null;

    try {

      return f.toURL();

    } catch(java.net.MalformedURLException e) {

      return null;

    }

  }

  private File findFile(String name, String extension) {

  // Поиск файла с именем name и, возможно, расширением extension в каталогах поиска, заданных параметром

  // конструктора classPath. Имена подкаталогов в name разделяются символом '/' – даже если в операционной

  // системе принят другой разделитель для подкаталогов. (Именно в таком виде получает свой параметр метод

  // findResource.)

    for (int k=0; k<classPath.length; k++) {

     File f= new File((new File(classPath[k])).getPath()+File.separatorChar+name.replace('/',File.separatorChar)+extension);

      if (f.exists()) return f;

    }

    return null;

  }

  public static byte[] loadFileAsBytes(File file)

    throws IOException

  {

    byte[] result= new byte[(int)file.length()];

    FileInputStream f= new FileInputStream(file);

    try {

      f.read(result,0,result.length);

    } finally {

      try {

        f.close();

      } catch (Exception e) {

           // Игнорируем исключения, возникшие при вызове close. Они крайне маловероятны и не очень

           // важны - файл уже прочитан. Но если они все же возникнут, то они не должны замаскировать

           // действительно важные ошибки, возникшие при вызове read.

      };

    }

    return result;

  }

 }
