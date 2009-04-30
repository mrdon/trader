package org.twdata.trader.ui

import org.fenggui.Button;
import org.fenggui.Container;
import org.fenggui.FengGUI;
import org.fenggui.Label;
import org.fenggui.event.ButtonPressedEvent;
import org.fenggui.event.IButtonPressedListener;
import org.fenggui.layout.Alignment;
import org.fenggui.layout.RowLayout;
import org.fenggui.util.Spacing
import org.fenggui.composites.Window
import org.fenggui.event.IWindowClosedListener
import org.fenggui.util.Dimension
import org.fenggui.layout.StaticLayout;

public class MessageWindow extends Window {

	private Closure onClose;
    private String title;
    private String message;
    private Container container;

	public MessageWindow(Map args) {
		super(true, false, false, true);
        args.each { key, value -> this.@"$key" = value }
        setupTheme(MessageWindow.class);
        container.addWidget(this);
		((Container)getContentContainer()).setLayoutManager(new RowLayout(false));

        setResizable(false);
		Label label = FengGUI.createLabel(getContentContainer());
        label.setText(message);
		label.getAppearance().setAlignment(Alignment.MIDDLE);
		label.getAppearance().setMargin(new Spacing(10, 10));

		Button OK = FengGUI.createButton(getContentContainer(), "OK");

		OK.getAppearance().setPadding(new Spacing(3, 10));
		OK.getAppearance().setMargin(new Spacing(5, 0, 0, 5));
		OK.setExpandable(false);
		OK.setSizeToMinSize();
        setSize(new Dimension(400, 100));
        updateMinSize();
        StaticLayout.center(this, container);
        setTitle(title);

		final Window thizz = this;
		OK.addButtonPressedListener({
            buttonPressed: {
                if (onClose) onClose();
                ((Container)getParent()).removeWidget(thizz);
			}} as IButtonPressedListener);
        addWindowClosedListener({
            windowClosed: {
                if (onClose) onClose();
                ((Container)getParent()).removeWidget(thizz);
            }
        } as IWindowClosedListener);
        layout();
	}
}