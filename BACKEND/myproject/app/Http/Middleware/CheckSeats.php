<?php

namespace App\Http\Middleware;

use App\Rides;
use Closure;
use http\Env\Response;

class CheckSeats
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        // i added first latley if something goes wrong just remove it
        $ride = Rides::find($request->ride_id)->first();
        if($ride->seats_booked >= $ride->seats_available){
            return response('the ride is already full',404);
      }
            return $next($request);

    }
}
