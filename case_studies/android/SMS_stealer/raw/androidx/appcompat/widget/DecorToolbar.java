package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.core.view.ViewPropertyAnimatorCompat;

public interface DecorToolbar {
  void animateToVisibility(int paramInt);
  
  boolean canShowOverflowMenu();
  
  void collapseActionView();
  
  void dismissPopupMenus();
  
  Context getContext();
  
  View getCustomView();
  
  int getDisplayOptions();
  
  int getDropdownItemCount();
  
  int getDropdownSelectedPosition();
  
  int getHeight();
  
  Menu getMenu();
  
  int getNavigationMode();
  
  CharSequence getSubtitle();
  
  CharSequence getTitle();
  
  ViewGroup getViewGroup();
  
  int getVisibility();
  
  boolean hasEmbeddedTabs();
  
  boolean hasExpandedActionView();
  
  boolean hasIcon();
  
  boolean hasLogo();
  
  boolean hideOverflowMenu();
  
  void initIndeterminateProgress();
  
  void initProgress();
  
  boolean isOverflowMenuShowPending();
  
  boolean isOverflowMenuShowing();
  
  boolean isTitleTruncated();
  
  void restoreHierarchyState(SparseArray<Parcelable> paramSparseArray);
  
  void saveHierarchyState(SparseArray<Parcelable> paramSparseArray);
  
  void setBackgroundDrawable(Drawable paramDrawable);
  
  void setCollapsible(boolean paramBoolean);
  
  void setCustomView(View paramView);
  
  void setDefaultNavigationContentDescription(int paramInt);
  
  void setDefaultNavigationIcon(Drawable paramDrawable);
  
  void setDisplayOptions(int paramInt);
  
  void setDropdownParams(SpinnerAdapter paramSpinnerAdapter, AdapterView.OnItemSelectedListener paramOnItemSelectedListener);
  
  void setDropdownSelectedPosition(int paramInt);
  
  void setEmbeddedTabView(ScrollingTabContainerView paramScrollingTabContainerView);
  
  void setHomeButtonEnabled(boolean paramBoolean);
  
  void setIcon(int paramInt);
  
  void setIcon(Drawable paramDrawable);
  
  void setLogo(int paramInt);
  
  void setLogo(Drawable paramDrawable);
  
  void setMenu(Menu paramMenu, MenuPresenter.Callback paramCallback);
  
  void setMenuCallbacks(MenuPresenter.Callback paramCallback, MenuBuilder.Callback paramCallback1);
  
  void setMenuPrepared();
  
  void setNavigationContentDescription(int paramInt);
  
  void setNavigationContentDescription(CharSequence paramCharSequence);
  
  void setNavigationIcon(int paramInt);
  
  void setNavigationIcon(Drawable paramDrawable);
  
  void setNavigationMode(int paramInt);
  
  void setSubtitle(CharSequence paramCharSequence);
  
  void setTitle(CharSequence paramCharSequence);
  
  void setVisibility(int paramInt);
  
  void setWindowCallback(Window.Callback paramCallback);
  
  void setWindowTitle(CharSequence paramCharSequence);
  
  ViewPropertyAnimatorCompat setupAnimatorToVisibility(int paramInt, long paramLong);
  
  boolean showOverflowMenu();
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\widget\DecorToolbar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */