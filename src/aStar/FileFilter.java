package aStar;

import java.io.File;

import javax.swing.filechooser.FileFilter;

class MyFilter extends FileFilter {
    private String endung;
   
    public MyFilter(String endung) {
        this.endung = endung;
    }

    @Override
    public String getDescription() {
        return endung + "-files";
    }

	@Override
	public boolean accept(File f) {
		if(f == null) return false;
	       
        // Ordner anzeigen
        if(f.isDirectory()) return true;
       
        // true, wenn File gewuenschte Endung besitzt
        return f.getName().toLowerCase().endsWith(endung);
	}
}