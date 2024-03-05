package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;

public class ListMenuItemView extends LinearLayout implements MenuView.ItemView, AbsListView.SelectionBoundsAdjuster {
  private static final String TAG = "ListMenuItemView";
  
  private Drawable mBackground;
  
  private CheckBox mCheckBox;
  
  private LinearLayout mContent;
  
  private boolean mForceShowIcon;
  
  private ImageView mGroupDivider;
  
  private boolean mHasListDivider;
  
  private ImageView mIconView;
  
  private LayoutInflater mInflater;
  
  private MenuItemImpl mItemData;
  
  private boolean mPreserveIconSpacing;
  
  private RadioButton mRadioButton;
  
  private TextView mShortcutView;
  
  private Drawable mSubMenuArrow;
  
  private ImageView mSubMenuArrowView;
  
  private int mTextAppearance;
  
  private Context mTextAppearanceContext;
  
  private TextView mTitleView;
  
  public ListMenuItemView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.listMenuViewStyle);
  }
  
  public ListMenuItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet);
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), paramAttributeSet, R.styleable.MenuView, paramInt, 0);
    this.mBackground = tintTypedArray.getDrawable(R.styleable.MenuView_android_itemBackground);
    this.mTextAppearance = tintTypedArray.getResourceId(R.styleable.MenuView_android_itemTextAppearance, -1);
    this.mPreserveIconSpacing = tintTypedArray.getBoolean(R.styleable.MenuView_preserveIconSpacing, false);
    this.mTextAppearanceContext = paramContext;
    this.mSubMenuArrow = tintTypedArray.getDrawable(R.styleable.MenuView_subMenuArrow);
    Resources.Theme theme = paramContext.getTheme();
    paramInt = R.attr.dropDownListViewStyle;
    TypedArray typedArray = theme.obtainStyledAttributes(null, new int[] { 16843049 }, paramInt, 0);
    this.mHasListDivider = typedArray.hasValue(0);
    tintTypedArray.recycle();
    typedArray.recycle();
  }
  
  private void addContentView(View paramView) {
    addContentView(paramView, -1);
  }
  
  private void addContentView(View paramView, int paramInt) {
    LinearLayout linearLayout = this.mContent;
    if (linearLayout != null) {
      linearLayout.addView(paramView, paramInt);
    } else {
      addView(paramView, paramInt);
    } 
  }
  
  private LayoutInflater getInflater() {
    if (this.mInflater == null)
      this.mInflater = LayoutInflater.from(getContext()); 
    return this.mInflater;
  }
  
  private void insertCheckBox() {
    CheckBox checkBox = (CheckBox)getInflater().inflate(R.layout.abc_list_menu_item_checkbox, (ViewGroup)this, false);
    this.mCheckBox = checkBox;
    addContentView((View)checkBox);
  }
  
  private void insertIconView() {
    ImageView imageView = (ImageView)getInflater().inflate(R.layout.abc_list_menu_item_icon, (ViewGroup)this, false);
    this.mIconView = imageView;
    addContentView((View)imageView, 0);
  }
  
  private void insertRadioButton() {
    RadioButton radioButton = (RadioButton)getInflater().inflate(R.layout.abc_list_menu_item_radio, (ViewGroup)this, false);
    this.mRadioButton = radioButton;
    addContentView((View)radioButton);
  }
  
  private void setSubMenuArrowVisible(boolean paramBoolean) {
    ImageView imageView = this.mSubMenuArrowView;
    if (imageView != null) {
      byte b;
      if (paramBoolean) {
        b = 0;
      } else {
        b = 8;
      } 
      imageView.setVisibility(b);
    } 
  }
  
  public void adjustListItemSelectionBounds(Rect paramRect) {
    ImageView imageView = this.mGroupDivider;
    if (imageView != null && imageView.getVisibility() == 0) {
      LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.mGroupDivider.getLayoutParams();
      paramRect.top += this.mGroupDivider.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    } 
  }
  
  public MenuItemImpl getItemData() {
    return this.mItemData;
  }
  
  public void initialize(MenuItemImpl paramMenuItemImpl, int paramInt) {
    this.mItemData = paramMenuItemImpl;
    if (paramMenuItemImpl.isVisible()) {
      paramInt = 0;
    } else {
      paramInt = 8;
    } 
    setVisibility(paramInt);
    setTitle(paramMenuItemImpl.getTitleForItemView(this));
    setCheckable(paramMenuItemImpl.isCheckable());
    setShortcut(paramMenuItemImpl.shouldShowShortcut(), paramMenuItemImpl.getShortcut());
    setIcon(paramMenuItemImpl.getIcon());
    setEnabled(paramMenuItemImpl.isEnabled());
    setSubMenuArrowVisible(paramMenuItemImpl.hasSubMenu());
    setContentDescription(paramMenuItemImpl.getContentDescription());
  }
  
  protected void onFinishInflate() {
    super.onFinishInflate();
    ViewCompat.setBackground((View)this, this.mBackground);
    TextView textView = (TextView)findViewById(R.id.title);
    this.mTitleView = textView;
    int i = this.mTextAppearance;
    if (i != -1)
      textView.setTextAppearance(this.mTextAppearanceContext, i); 
    this.mShortcutView = (TextView)findViewById(R.id.shortcut);
    ImageView imageView = (ImageView)findViewById(R.id.submenuarrow);
    this.mSubMenuArrowView = imageView;
    if (imageView != null)
      imageView.setImageDrawable(this.mSubMenuArrow); 
    this.mGroupDivider = (ImageView)findViewById(R.id.group_divider);
    this.mContent = (LinearLayout)findViewById(R.id.content);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    if (this.mIconView != null && this.mPreserveIconSpacing) {
      ViewGroup.LayoutParams layoutParams = getLayoutParams();
      LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams)this.mIconView.getLayoutParams();
      if (layoutParams.height > 0 && layoutParams1.width <= 0)
        layoutParams1.width = layoutParams.height; 
    } 
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public boolean prefersCondensedTitle() {
    return false;
  }
  
  public void setCheckable(boolean paramBoolean) {
    CheckBox checkBox;
    RadioButton radioButton;
    if (!paramBoolean && this.mRadioButton == null && this.mCheckBox == null)
      return; 
    if (this.mItemData.isExclusiveCheckable()) {
      if (this.mRadioButton == null)
        insertRadioButton(); 
      RadioButton radioButton1 = this.mRadioButton;
      CheckBox checkBox1 = this.mCheckBox;
    } else {
      if (this.mCheckBox == null)
        insertCheckBox(); 
      checkBox = this.mCheckBox;
      radioButton = this.mRadioButton;
    } 
    if (paramBoolean) {
      checkBox.setChecked(this.mItemData.isChecked());
      if (checkBox.getVisibility() != 0)
        checkBox.setVisibility(0); 
      if (radioButton != null && radioButton.getVisibility() != 8)
        radioButton.setVisibility(8); 
    } else {
      checkBox = this.mCheckBox;
      if (checkBox != null)
        checkBox.setVisibility(8); 
      RadioButton radioButton1 = this.mRadioButton;
      if (radioButton1 != null)
        radioButton1.setVisibility(8); 
    } 
  }
  
  public void setChecked(boolean paramBoolean) {
    CheckBox checkBox;
    if (this.mItemData.isExclusiveCheckable()) {
      if (this.mRadioButton == null)
        insertRadioButton(); 
      RadioButton radioButton = this.mRadioButton;
    } else {
      if (this.mCheckBox == null)
        insertCheckBox(); 
      checkBox = this.mCheckBox;
    } 
    checkBox.setChecked(paramBoolean);
  }
  
  public void setForceShowIcon(boolean paramBoolean) {
    this.mForceShowIcon = paramBoolean;
    this.mPreserveIconSpacing = paramBoolean;
  }
  
  public void setGroupDividerEnabled(boolean paramBoolean) {
    ImageView imageView = this.mGroupDivider;
    if (imageView != null) {
      byte b;
      if (!this.mHasListDivider && paramBoolean) {
        b = 0;
      } else {
        b = 8;
      } 
      imageView.setVisibility(b);
    } 
  }
  
  public void setIcon(Drawable paramDrawable) {
    boolean bool;
    if (this.mItemData.shouldShowIcon() || this.mForceShowIcon) {
      bool = true;
    } else {
      bool = false;
    } 
    if (!bool && !this.mPreserveIconSpacing)
      return; 
    if (this.mIconView == null && paramDrawable == null && !this.mPreserveIconSpacing)
      return; 
    if (this.mIconView == null)
      insertIconView(); 
    if (paramDrawable != null || this.mPreserveIconSpacing) {
      ImageView imageView = this.mIconView;
      if (!bool)
        paramDrawable = null; 
      imageView.setImageDrawable(paramDrawable);
      if (this.mIconView.getVisibility() != 0)
        this.mIconView.setVisibility(0); 
      return;
    } 
    this.mIconView.setVisibility(8);
  }
  
  public void setShortcut(boolean paramBoolean, char paramChar) {
    if (paramBoolean && this.mItemData.shouldShowShortcut()) {
      paramChar = Character.MIN_VALUE;
    } else {
      paramChar = '\b';
    } 
    if (paramChar == '\000')
      this.mShortcutView.setText(this.mItemData.getShortcutLabel()); 
    if (this.mShortcutView.getVisibility() != paramChar)
      this.mShortcutView.setVisibility(paramChar); 
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    if (paramCharSequence != null) {
      this.mTitleView.setText(paramCharSequence);
      if (this.mTitleView.getVisibility() != 0)
        this.mTitleView.setVisibility(0); 
    } else if (this.mTitleView.getVisibility() != 8) {
      this.mTitleView.setVisibility(8);
    } 
  }
  
  public boolean showsIcon() {
    return this.mForceShowIcon;
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\appcompat\view\menu\ListMenuItemView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */