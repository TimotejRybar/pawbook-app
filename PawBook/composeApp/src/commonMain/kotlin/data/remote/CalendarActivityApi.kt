package data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query
import domain.model.CalendarActivity
import domain.model.result.FetchCalendarActivitiesResult
import domain.model.result.FetchCalendarForMonthResult
import kotlinx.datetime.LocalDate

interface CalendarActivityApi
{
    @GET("calendar")
    suspend fun fetch(): FetchCalendarActivitiesResult

    @GET("calendar/month")
    suspend fun fetchForMonth(@Query("month") month: LocalDate): FetchCalendarForMonthResult

    @POST("calendar")
    suspend fun createCalendarActivity(@Body calendarActivity: CalendarActivity): CalendarActivity?

}