package com.bq.zowi.views.interactive.pad;

import com.bq.zowi.models.commands.GridCommand;
import com.bq.zowi.views.interactive.InteractiveBaseView;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface PadView extends InteractiveBaseView {
    void setUnlockStatusCrusaitoButton(boolean z);

    void setUnlockStatusFlappingButton(boolean z);

    void setUnlockStatusJitterButton(boolean z);

    void setUnlockStatusShakeLegButton(boolean z);

    void setUnlockStatusSwingButton(boolean z);

    void showActionsGrid(List<GridCommand> list);

    void showHelp();
}
