package org.glob3.mobile.generated;
public class IconDownloadListener extends IImageDownloadListener
{
  private Mark _mark;
  private final String _label;
  private final boolean _labelBottom;
  private final float _labelFontSize;
  private final Color _labelFontColor;
  private final Color _labelShadowColor;
  private final int _labelGapSize;

  private boolean _markHasBeenDeleted;

  public IconDownloadListener(Mark mark, String label, boolean labelBottom, float labelFontSize, Color labelFontColor, Color labelShadowColor, int labelGapSize)
  {
     _mark = mark;
     _label = label;
     _labelBottom = labelBottom;
     _labelFontSize = labelFontSize;
     _labelFontColor = labelFontColor;
     _labelShadowColor = labelShadowColor;
     _labelGapSize = labelGapSize;
     _markHasBeenDeleted = false;

  }

  public final void markHasBeenDeleted()
  {
    _markHasBeenDeleted = true;
  }

  public final void onDownload(URL url, IImage image, boolean expired)
  {
    _mark.resetRequestIconId();
    final boolean hasLabel = (_label.length() != 0);

    if (hasLabel)
    {
      LabelPosition labelPosition = _labelBottom ? LabelPosition.Bottom : LabelPosition.Right;

      ITextUtils.instance().labelImage(image, _label, labelPosition, _labelGapSize, _labelFontSize, _labelFontColor, _labelShadowColor, new MarkLabelImageListener(image, _mark), true);
    }
    else
    {
      _mark.onTextureDownload(image);
    }
  }

  public final void onError(URL url)
  {
    ILogger.instance().logError("Error trying to download image \"%s\"", url._path);
    if (!_markHasBeenDeleted)
    {
      _mark.resetRequestIconId();
      _mark.onTextureDownloadError();
    }
  }

  public final void onCancel(URL url)
  {
    if (!_markHasBeenDeleted)
    {
      _mark.resetRequestIconId();
      // ILogger::instance()->logError("Download canceled for image \"%s\"", url._path.c_str());
      _mark.onTextureDownloadError();
    }
  }

  public final void onCanceledDownload(URL url, IImage image, boolean expired)
  {
    if (!_markHasBeenDeleted)
    {
      _mark.resetRequestIconId();
    }
  }
}
