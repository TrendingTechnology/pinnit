package dev.sasikanth.pinnit.editor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Test
import java.util.UUID

class EditorScreenUiRenderTest {

  private val ui = mock<EditorScreenUi>()
  private val uiRender = EditorScreenUiRender(ui)

  @Test
  fun `when notification is being fetched, then do nothing`() {
    // given
    val model = EditorScreenModel.default(null)
      .titleChanged(null)

    // when
    uiRender.render(model)

    // then
    verifyZeroInteractions(ui)
  }

  @Test
  fun `when notification is fetch, then update ui`() {
    // given
    val notificationUuid = UUID.fromString("ad9e2ac7-9c12-4f5a-baba-460cacc65dc8")
    val notificationTitle = "Notification Title"
    val notificationContent = "Notification Content"

    val model = EditorScreenModel.default(notificationUuid)
      .titleChanged(notificationTitle)
      .contentChanged(notificationContent)

    // then
    uiRender.render(model)

    // then
    verify(ui).setTitle(notificationTitle)
    verify(ui).setContent(notificationContent)
    verify(ui).enableSave()
    verifyNoMoreInteractions(ui)
  }

  @Test
  fun `when title is not blank, then enable save`() {
    val notificationUuid = UUID.fromString("ad9e2ac7-9c12-4f5a-baba-460cacc65dc8")
    val notificationTitle = "Notification Title"

    val model = EditorScreenModel.default(notificationUuid)
      .titleChanged(notificationTitle)

    // then
    uiRender.render(model)

    // then
    verify(ui).setTitle(notificationTitle)
    verify(ui).setContent(null)
    verify(ui).enableSave()
    verifyNoMoreInteractions(ui)
  }

  @Test
  fun `when title is blank, then disable save`() {
    val notificationUuid = UUID.fromString("ad9e2ac7-9c12-4f5a-baba-460cacc65dc8")
    val title = ""

    val model = EditorScreenModel.default(notificationUuid)
      .titleChanged(title)

    // then
    uiRender.render(model)

    // then
    verify(ui).setTitle(title)
    verify(ui).setContent(null)
    verify(ui).disableSave()
    verifyNoMoreInteractions(ui)
  }
}