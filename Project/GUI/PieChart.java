package GUI;

import java.text.AttributedString;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

public class PieChart {
	
	 public PieChart (final String title) {
	    final PieDataset dataset = createSampleDataset();
	    final JFreeChart chart = createChart(dataset);
	    final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	    setContentPane(chartPanel);
	 }
	 
	 private PieDataset createSampleDataset() {
	        final DefaultPieDataset result = new DefaultPieDataset();
	        //Ubaciti vrednosti reci:
	        /*result.setValue("Java", new Double(43.2));
	        result.setValue("Visual Basic", new Double(10.0));
	        result.setValue("C/C++", new Double(17.5));
	        result.setValue("PHP", new Double(32.5));
	        result.setValue("Perl", new Double(1.0));*/
	        return result;
	        
	    }
	 
	 private JFreeChart createChart(final PieDataset dataset) {
	        
		 final JFreeChart chart = ChartFactory.createPieChart3D(
			"Words probability",  // chart title
	        dataset,                // data
	        true,                   // include legend
	        true,
	        false
	     );

		 final PiePlot3D plot = (PiePlot3D) chart.getPlot();
		 plot.setStartAngle(290);
		 plot.setDirection(Rotation.CLOCKWISE);
		 plot.setForegroundAlpha(0.5f);
		 plot.setNoDataMessage("No data to display");
		// plot.setLabelGenerator(new CustomLabelGenerator());
		 return chart;
	 }
	 
	 static class CustomLabelGenerator implements PieSectionLabelGenerator {
	        
	        /**
	         * Generates a label for a pie section.
	         * 
	         * @param dataset  the dataset (<code>null</code> not permitted).
	         * @param key  the section key (<code>null</code> not permitted).
	         * 
	         * @return the label (possibly <code>null</code>).
	         */
	        public String generateSectionLabel(final PieDataset dataset, final Comparable key) {
	            String result = null;    
	            if (dataset != null) {
	                if (!key.equals("PHP")) {
	                    result = key.toString();   
	                }
	            }
	            return result;
	        }

			@Override
			public AttributedString generateAttributedSectionLabel(PieDataset arg0, Comparable arg1) {
				// TODO Auto-generated method stub
				return null;
			}
	   
	    }

}
